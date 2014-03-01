$(document).ready(function(){
	/*
	 * Transform all checkboxes and radiobuttons.	
	 */
	
	$('input').icheck({
		checkboxClass: 'icheckbox_flat',
		radioClass: 'iradio_flat'
		//increaseArea: '20%' // optional
	});
	
	/*
	 * Transform all selections
	 */
	$('.selectpicker').selectpicker();
	$('.selectblocks').selectblocks();
	$('.has-html-popover').popover({ html: true });
	
	$('.datepicker').datepicker({ format: "yyyy-mm-dd" });
	
	$(".nav-list li a").each(function(i, elem) {
		if (endsWith(document.URL, $(elem).attr("href"))) {
			$(elem).parent().addClass("active");
		}
	})

	$(".pc-nav-list li a").each(function(i, elem) {
        if (endsWith(document.URL, $(elem).attr("href"))) {
            $(elem).parent().addClass("active");
        }
    })
});

function endsWith(str, suffix) {
    return str.indexOf(suffix, str.length - suffix.length) !== -1;
}