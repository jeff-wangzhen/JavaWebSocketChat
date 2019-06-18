var i = 0;
self.postMessage({"start": true});
var t = setInterval(function () {
  i++;
  if (i === 1) {
    self.postMessage({"i": i, "timeOut": true});
  } else {
    self.postMessage({"i": i, "timeOut": false});
  }
}, 10 * 60 * 1000);
self.addEventListener('message', function (e) {
  var data = e.data;
  if (data.cmd==='reset'){
      i = 0;
      clearInterval(t);
      t = setInterval(function () {
        i++;
        if (i === 1) {
          self.postMessage({"i": i, "timeOut": true});
        } else {
          self.postMessage({"i": i, "timeOut": false});
        }
      }, 10 * 60 * 1000);
  }

}, false);