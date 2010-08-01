log = function(msg) {
	if (console != null) {
		console.log(msg);
	}
};

r = function() {
	if (window.persistentRequest != null) {
		window.persistentRequest.abort();
	}
};

window.persistentRequest = null;

start_delimiter = "<s" + "cript type='text/javascript'>";
end_delimiter = "</" + "script>";

window.longRequest = function() {
	streamreq = new XMLHttpRequest();
	byteoffset = 0;
	var url = "/game?nuclear_launch_code=" + Math.random();//Math.floor(Math.random() * 10000000);
	streamreq.open("GET", url, true);
	streamreq.onreadystatechange = function() {
		if (typeof streamreq == "undefined")
			return;
		if (streamreq.readyState == 3) {
			var buffer = streamreq.responseText;
			var newdata = buffer.substring(byteoffset);
			byteoffset = buffer.length;
			while (1) {
				var x = newdata.indexOf(start_delimiter);
				if (x != -1) {
					y = newdata.indexOf(end_delimiter, x);
					if (y != -1) {
						//alert(newdata.substring((x + start_delimiter.length), y));
						eval(newdata.substring((x + start_delimiter.length), y));
						newdata = newdata.substring(y + end_delimiter.length);
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
			if (typeof (r) == "function") {
				r();
			}
			delete streamreq;
		}
	};
	streamreq.send(null);
};
