function loadCfs() {
	var config = document.getElementById("pageInfo").value
	var page = document.getElementById("configInfo").value
	var z = document.getElementById("nextPage").value
	if (config==page) {
		if (z != 'N/A') {
			var x = document.getElementById('selectSite');
			var opt = document.createElement('option');
			opt.value = page;
			opt.innerHTML = page;
			// c.text = result[i].getAttribute('site');
			x.appendChild(opt);
		}
		if (z == 'N/A') {
			var x = document.getElementById('selectPage');
			var opt = document.createElement('option');
			opt.value = page;
			opt.innerHTML = page;
			// c.text = result[i].getAttribute('site');
			x.appendChild(opt);
		}

	}
}