<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>对不起，你访问的页面不存在</title>
  <style>
    body {
      padding: 0;
      margin: 0;
      background: #77ae6d;
    }

    .content {
      width: 1000px;
      margin: 0 auto;
      position: relative;
    }

    .bg
    {
      position: absolute;
      left: 45px;
      top: 50px;
      width: 910px;
      height: 611px;
      background: url("res/lowimg.png") no-repeat 0 0;
      background-position: 0 0;
    }

    .home {
      position: absolute;
      left:118px;
      top: 373px;
      width: 200px;
      height: 40px;
      background: url("res/lowimg.png") no-repeat 0 0;
      background-position: 0 -620px;
    }

    .home:hover{
      background-position: 0 -670px;
    }

    .home:active{
      background-position: 0 -720px;
    }

    .goon {
      position: absolute;
      left:338px;
      top: 373px;
      width: 200px;
      height: 40px;
      background: url("res/lowimg.png") no-repeat 0 0;
      background-position: -220px -620px;
    }

    .goon:hover{
      background-position: -220px -670px;
    }

    .goon:active{
      background-position: -220px -720px;
    }

  </style>
</head>
<body monkey="error-404-low">
  <div class="content">
     <div class="bg">
       <a alog-text="返回首页" id="home" class="home"></a>
       <a alog-text="继续访问" id="goon" class="goon"></a>
     </div>
  </div>
  <script>
    function _hasOwnProperty(obj, prop) {
      return Object.prototype.hasOwnProperty.call(obj, prop);
    }
    function _isArray(obj) {
      var toString = Object.prototype.toString;
      return '[object Array]' == toString.call(obj);
    }

    function ParseQuery(qs, sep, eq, options) {
      sep = sep || '&';
      eq = eq || '=';
      var obj = {};

      if (typeof qs !== 'string' || qs.length === 0) {
        return obj;
      }

      var regexp = /\+/g;
      qs = qs.split(sep);

      var maxKeys = 1000;
      if (options && typeof options.maxKeys === 'number') {
        maxKeys = options.maxKeys;
      }

      var len = qs.length;
      // maxKeys <= 0 means that we should not limit keys count
      if (maxKeys > 0 && len > maxKeys) {
        len = maxKeys;
      }

      for (var i = 0; i < len; ++i) {
        var x = qs[i].replace(regexp, '%20'),
            idx = x.indexOf(eq),
            kstr, vstr, k, v;

        if (idx >= 0) {
          kstr = x.substr(0, idx);
          vstr = x.substr(idx + 1);
        } else {
          kstr = x;
          vstr = '';
        }

        try {
          k = decodeURIComponent(kstr);
          v = decodeURIComponent(vstr);
        } catch (e) {
          //k = QueryString.unescape(kstr, true);
          //v = QueryString.unescape(vstr, true);
          //无法解码，则继续
          continue;
        }

        if (!_hasOwnProperty(obj, k)) {
          obj[k] = v;
        } else if (_isArray(obj[k])) {
          obj[k].push(v);
        } else {
          obj[k] = [obj[k], v];
        }
      }

      return obj;
    }

    function addHref() {
      var search = location.search.replace(/^\?+/g, ""),
          query = ParseQuery(search),
          goon = document.getElementById("goon"),
          home = document.getElementById("home");

      goon.setAttribute('href', query['from']);
      home.setAttribute('href', '${path}/index.jsp');
    }

    addHref();

    var script = document.createElement("script");
    script.src = "${path}/js/track.js?" + ~(+new Date / 36e5);
    script.setAttribute("data-log-config", "pageId:hao123-error-404-low;page:hao123-error-404-low;level:2");
    document.body.appendChild(script);

  </script>
</body>
</html>