/* Routes blob: downloads (backup JSON, jsPDF export) to the Android "Save as" dialog. */
(function () {
  if (window.__nexusDlHook) return;
  window.__nexusDlHook = true;

  function sendBlob(href, name) {
    fetch(href)
      .then(function (r) { return r.blob(); })
      .then(function (b) {
        var fr = new FileReader();
        fr.onloadend = function () {
          var res = String(fr.result || '');
          var i = res.indexOf(',');
          window.AndroidBridge.saveFile(
            res.substring(i + 1),
            b.type || 'application/octet-stream',
            name || 'download'
          );
        };
        fr.readAsDataURL(b);
      })
      .catch(function () {});
  }

  function intercept(a) {
    if (a && a.hasAttribute && a.hasAttribute('download') && a.href && a.href.indexOf('blob:') === 0) {
      sendBlob(a.href, a.getAttribute('download'));
      return true;
    }
    return false;
  }

  var origClick = HTMLAnchorElement.prototype.click;
  HTMLAnchorElement.prototype.click = function () {
    if (intercept(this)) return;
    return origClick.apply(this, arguments);
  };

  var origDispatch = HTMLAnchorElement.prototype.dispatchEvent;
  HTMLAnchorElement.prototype.dispatchEvent = function (e) {
    if (e && e.type === 'click' && intercept(this)) return true;
    return origDispatch.apply(this, arguments);
  };

  document.addEventListener('click', function (e) {
    var a = e.target && e.target.closest ? e.target.closest('a[download]') : null;
    if (a && intercept(a)) e.preventDefault();
  }, true);
})();
