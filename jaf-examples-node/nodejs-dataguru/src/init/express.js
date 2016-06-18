'use strict';

/**
* @author: walle <liaozhicheng.cn@163.com>
*/

import path from 'path';
import express from 'express';
import serveStatic from 'serve-static';
import bodyParser from 'body-parser';
import multipart from 'connect-multiparty';

module.exports = function(done) {

  const debug = $.debug('express');
  debug('init express');

  const app = express();

  app.use(bodyParser.json());
  app.use(bodyParser.urlencoded({extended: false}));

  const router = express.Router();
  $.router = router;

  app.use(router);
  app.use('/static', serveStatic(path.resolve(__dirname, '../../static')));

  // 监听端口
  app.listen($.config.get('web.port'), (err) => {
    done(err);
  });

  done();
}
