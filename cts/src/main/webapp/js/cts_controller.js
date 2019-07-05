
ctsApp.controller('mapInit', function($rootScope, $scope, $window, click_radius){
	$window.initMap = function(){
		var map = new google.maps.Map(document.getElementById('google_map'), {
			center:new google.maps.LatLng(47.497912, 19.040235),
			zoom:11
		});
		
		var marker = new google.maps.Marker({
			position:{lat:47.497912, lng:19.040235},
			map: map,
			title: 'Center of Budapest'
		});
		
		var searchBox = new google.maps.places.SearchBox(getById('place'));
		
		var mapEventLst = google.maps.event.addListener(map, 'click', function(event){
			click_radius.addClickLst(event, $rootScope);
		});
		
		$window.map = map;
		$window.searchBox = searchBox;
	}
});
