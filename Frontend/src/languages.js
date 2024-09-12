export const languages =
{
    'Bash':{
      highlighting:"shell",
      ext: 'sh',
      kor: '리눅스 셸',
      template:"bash",
      command:"chmod 755 {FILENAME}.{EXT} && bash {ARGS} ./{FILENAME}.{EXT}",
      cli: null,
      errorRegEx: ".*\\.sh: .+? (\\d+): (.*)"
    },
    'Python3':{
      highlighting:"python",
      ext: 'py',
      kor: '파이썬3',
      template:"python",
      command:"python3 {ARGS} {FILENAME}.{EXT}",
      cli: 'python3 {ARGS}',
      errorRegEx: ".+?\\.py\", line (\\d+)(?:.*?\r\n)+?(.*?Error: .*)"
    },
    
    'TypeScript':{
      highlighting:"typescript1",
      ext: 'ts',
      kor: '타입스크립트',
      template:"typescript",
      args: "--pretty false --target es2015",
      command:"tsc {ARGS} {FILENAME}.{EXT} && node {FILENAME}.js",
      cli: 'ts-node',
      errorRegEx: ".+?\\((\\d+),\\d+\\): (.+)"
    },
    
    'JS - Node.js':{
      highlighting:"javascript1",
      ext: 'js',
      kor: '노드.js (Javascript, 자바스크립트)',
      template:"javascript",
      command:"node {ARGS} {FILENAME}.{EXT}",
      cli: 'node {ARGS}',
      errorRegEx: ".+:(\\d+)\r\n.+\r\n.+\r\n\r\n(.+)\r\n"
    },
    
    'java':{
      highlighting:"java",
      ext: 'java',
      kor: '자바',
      template:"java",
      command:"javac {ARGS} {FILENAME}.{EXT} && java {FILENAME}",
      cli: 'jshell',
      defaultFilename: 'Main',
      errorRegEx: ".+?:(\\d+): (\\w+: .+)"
    },
    
    'C':{
      highlighting:"c",
      ext: 'c',
      kor: 'C언어 - gcc',
      template:"c",
      args: '-Wall -lm',
      command:"gcc -fdiagnostics-color=never -o {FILENAME} {FILENAME}.{EXT} {ARGS} && ./{FILENAME}",
      cli: null,
      errorRegEx: ".+?\\.c:(\\d+):\\d+: error: (.*)"
    },
    
    'C++':{
      highlighting:"cpp",
      ext: 'cc',
      kor: 'C++ - g++',
      template:"cc",
      args: '-Wall -lboost_system -lboost_thread -pthread -ltbb',
      command:"g++ -fdiagnostics-color=never -o {FILENAME} {FILENAME}.{EXT} {ARGS} && ./{FILENAME}",
      cli: null,
      errorRegEx: ".+?\\.cc:(\\d+):\\d+: error: (.*)",
    },
    'Kotlin':{
      highlighting:"kotlin",
      ext: 'kt',
      kor: '코틀린',
      template:"kotlin",
      args: '-include-runtime',
      command:"kotlinc {FILENAME}.{EXT} {ARGS} -d {FILENAME}.jar && java -jar {FILENAME}.jar",
      cli: "kotlin",
      errorRegEx: ".+?:(\\d+):\\d+: .+: (.*)",
    },
    'MySQL(mariadb)':{
      highlighting:"mysql",
      ext: 'sql',
      kor: '마이SQL - mariadb',
      template:"mysql",
      args: '-D bluecode',
      command:"ps -ef | grep mysqld | grep -v grep > /dev/null || sudo service mysql start\nmysql {ARGS} -H < {FILENAME}.{EXT} > {FILENAME}.html && imshow {FILENAME}.html",
      cli: "ps -ef | grep mysqld | grep -v grep > /dev/null || sudo service mysql start\nmysql {ARGS}",
      errorRegEx: ".+? at line (\\d+): (.+)"
    }
};