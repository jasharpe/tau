log = function(msg) {
	if (console != null) {
		console.log(msg);
	}
};

r = function() {
	if (persistentRequest != null) {
		persistentRequest.abort();
	}
};

persistentRequest = null;

startDelimiter = "<s" + "cript type='text/javascript'>";
endDelimiter = "</" + "script>";

window.intentionalRestart = false;

get_ready = false;

longRequest = function() {
	streamreq = new XMLHttpRequest();
	byteoffset = 0;
	var url = "/game?nuclear_launch_code=" + Math.random();
	streamreq.open("GET", url, true);
	streamreq.onreadystatechange = function() {
		if (typeof streamreq == "undefined")
			return;
		if (streamreq.readyState == 3) {
			if (!get_ready) {
				window.initialize();
				get_ready = true;
			}
			if (!window.longRequestReady) {
				window.longRequestReady = true;
			}
			var buffer = streamreq.responseText;
			var newdata = buffer.substring(byteoffset);
			byteoffset = buffer.length;
			while (1) {
				var x = newdata.indexOf(startDelimiter);
				if (x != -1) {
					y = newdata.indexOf(endDelimiter, x);
					if (y != -1) {
						//alert(newdata.substring((x + start_delimiter.length), y));
						eval(newdata.substring((x + startDelimiter.length), y));
						newdata = newdata.substring(y + endDelimiter.length);
					} else {
						// Last message is incomplete. Ignore it and it will be
						// fetched again
						break;
					}
				} else {
					// No more messages
					break;
				}
			}
			byteoffset = buffer.length - newdata.length;
		} else if (streamreq.readyState == 4) {
			r();
			delete streamreq;
			if (!window.intentionalRestart) {
				// try to restart the connection in 500ms
				setTimeout('window.restart()', 500);
			}
		}
	};
	persistentRequest = streamreq;
	streamreq.send(null);
};

window.l = function() {
	r();
	longRequest();
};