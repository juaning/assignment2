/**
 * Juan I. Mignaco
 */

// Slider handler
$('#maxPrice').slider({
	formater: function(value) {
		return '$' + value;
	}
}).on('slide', function(slideEvt){
	$('#selMaxPrice').val(slideEvt.value);
	$('.slider-handle.round').trigger('click');
});

// Form check
$('#consumerSearchForm').on('submit', function(){
	var retVal = true;
	var alertMsg = '';
	$('#displayAlerts').find('ul').html('');
	if($('#checkin').parent('.form-group').hasClass('has-error has-feedback')) {
		$('#checkin').parent('.form-group').removeClass('has-error has-feedback');
		$('#checkin').parent('.form-group').find('span').remove();
	}
	if($('#checkout').parent('.form-group').hasClass('has-error has-feedback')) {
		$('#checkout').parent('.form-group').removeClass('has-error has-feedback');
		$('#checkout').parent('.form-group').find('span').remove();
	}
	if($('#checkin').val() === "") {
		$('#checkin').parent('.form-group').addClass('has-error has-feedback');
		$('#checkin').parent('.form-group').append('<span class="glyphicon glyphicon-remove form-control-feedback"></span>');
		alertMsg += '<li>Check-in date can not be empty</li>';
		retVal = false;
	}
	if($('#checkout').val() === "") {
		$('#checkout').parent('.form-group').addClass('has-error has-feedback');
		$('#checkout').parent('.form-group').append('<span class="glyphicon glyphicon-remove form-control-feedback"></span>');
		alertMsg += '<li>Check-ou date can not be empty</li>';
		retVal = false;
	}
	if($('#city').val() === "0") {
		alertMsg += '<li>Please select a City</li>';
		retVal = false;
	}
	if(!retVal) $('#displayAlerts').find('ul').append(alertMsg);
	return retVal;
});

$(document).ready(function(){
	$('#consumerConfirmForm .checkbox').on("click", function(){
		if($(this).is(':checked')) {
			var total = parseInt($('.confirmTotal').html());
			$('.confirmTotal').html(total + 35);
			$('#totalPrice').val(total + 35);
		} else {
			var total = parseInt($('.confirmTotal').html());
			$('.confirmTotal').html(total - 35);
			$('#totalPrice').val(total - 35);
		}
	});
});