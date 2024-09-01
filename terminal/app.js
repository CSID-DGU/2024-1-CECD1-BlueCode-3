#!/usr/local/bin/node

const express = require('express')
const serveIndex = require('serve-index')
const pty = require('node-pty')
const crypto = require('crypto')
const { spawn } = require("child_process")
const http = require('http')
const websocket = require('ws')
const app = express()
app.disable('x-powered-by')
const base_dir = '/home/bluecode'
const port = 5000

let dockerCount = 0

app.use(express.static(base_dir + '/public'))
app.use(express.json({limit : "1gb"}));
app.use(express.urlencoded({limit : "1mb", extended: false}));
app.use('/list', serveIndex(base_dir + '/contents'))


hash = () => {
    return crypto.createHmac('sha256', 'mysharedsource01').update(Date.now().toString()).digest('base64').replace('/', '_').substr(0, 10)
}

app.post('/', (req, res) => {
    const path = base_dir + '/public/index.html'
    res.sendFile(path)
})


const server = http.createServer(app).listen(port, () => {
    console.log(`app listening at http://localhost:${port}`)
})

const websocketServer = new websocket.Server(
    {server, path: '/terminal'}
)

setInterval(function ping() {
	if (websocketServer.clients.size > 0) {
		websocketServer.clients.forEach(function each(socket) {
			if (socket.isAlive === false) return socket.terminate()

			socket.isAlive = false;
			socket.ping(websocketServer.clients.size)
            socket.send('2' + websocketServer.clients.size)
		})
	}
}, 5000)

let docker_seq = 0

websocketServer.on('connection', (ws, req) => {
    docker_seq = docker_seq > 99999999 ? 0 : docker_seq+1
    const docker_name = 'RS' + `0000000${docker_seq}`.slice(-8)

    var ip = req.headers['x-forwarded-for'] || req.connection.remoteAddress
    var lang = req.headers["accept-language"]
    var locale = (lang && lang.split(',')[0].indexOf("ko") >= 0)?'ko_KR':'C'
    console.log(new Date().toString(), 'connected...', ip, docker_name, lang)

    const child = pty.spawn('/usr/bin/docker', [
        'run',
        '--env',
        `LANG=${locale}.UTF-8`,
        '--env',
        'TMOUT=1200',
        '--env',
        `DOCKER_NAME=${docker_name}`,
        '-it',
        '--name',
        docker_name,
        '--rm',
        '--workdir',
        '/home/bluecode',
        '--user',
        'bluecode',
        '--hostname',
        'bluecode-server',
        'ubuntu:bluecode',
        '/bin/bash'
    ], {
        name: 'xterm-color',
    })
    console.log('forking docker: ', docker_name, child.pid)
    dockerCount++

    child.onData((data) => {
        console.log("onData: " + data)
        ws.send('1' + data.toString())
    })
    child.onExit((code) => {
        ws.close()
        dockerCount--
        console.log('child closed', docker_name, child.pid, code)
    })

    ws.on('message', (message) => {
        const cmd = message.toString()[0]
        console.log("message: " + message)
        console.log("cmd:" + cmd)
        switch (cmd) {
        case '1':
            if (message) {
                const msg = message.slice(1)
                console.log("msg: " + msg)
                child.write(msg)
            }
            break
        case '2': /* resize */
            const size = message.toString().split(' ')
            child.resize(parseInt(size[1]), parseInt(size[2]))
            break
        }
    })
    ws.on('close', (e) => {
        spawn('docker', ['kill', docker_name]).on('close', code => {            
            console.log('socket closed...', new Date().toString(), docker_name, child.pid, e)
        })
    })
    ws.on('error', (err) => {
        console.log('error occured', err)
    })
    ws.on('pong', () => {
        ws.isAlive = true
    })
})
