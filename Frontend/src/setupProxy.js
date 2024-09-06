const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(
    ['/api', '/user','/checkAuth','/curriculum','/mission','/test','/chat'],
    createProxyMiddleware({
      target: 'http://3.37.159.243:8080',
      changeOrigin: true,
    })
  );
};