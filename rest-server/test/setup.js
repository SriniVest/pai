// Copyright (c) Microsoft Corporation
// All rights reserved.
//
// MIT License
//
// Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
// documentation files (the "Software"), to deal in the Software without restriction, including without limitation
// the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
// to permit persons to whom the Software is furnished to do so, subject to the following conditions:
// The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED *AS IS*, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
// BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
// NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
// DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.


// environment variables
process.env.NODE_ENV = 'test';
process.env.SERVER_PORT = 8080;
process.env.HDFS_URI = 'hdfs://hdfs.test.pai:9000';
process.env.LAUNCHER_WEBSERVICE_URI = 'http://launcher.test.pai:9086';
process.env.JWT_SECRET = 'jwt_test_secret';
process.env.LOWDB_FILE = './user.db.json';
process.env.LOWDB_ADMIN = 'iamadmin';
process.env.LOWDB_PASSWD = 'adminisi';


// module dependencies
const nock = require('nock');
const chai = require('chai');
const chaiHttp = require('chai-http');
const server = require('../src/index');

chai.use(chaiHttp);


global.nock = nock;
global.chai = chai;
global.assert = chai.assert;
global.expect = chai.expect;
global.should = chai.should;
global.server = server;
global.launcherWebserviceUri = process.env.LAUNCHER_WEBSERVICE_URI;