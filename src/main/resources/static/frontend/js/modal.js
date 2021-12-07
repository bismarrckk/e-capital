	$(document).ready(function(){
	$('.table #delBtn').on('click', function(event){
		event.preventDefault();
		var href =$(this).attr('href');
		$('#exampleModalLong #delRef').attr('href',href);
		$('#exampleModalLong').modal();
		
	});
});
	
	$(document).ready(function(){
	$('#helpBtn').on('click', function(event){
		event.preventDefault();
		$('#helpModal').modal();
		
	});
});