function getById(elemName){
	return document.getElementById(elemName);
}

ctsApp.controller('mapInit', function($rootScope, $scope, $window){ //click_radius
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
		
		// var mapEventLst = google.maps.event.addListener(map, 'click', function(event){
		// 	click_radius.addClickLst(event, $rootScope);
		// });
		
		$window.map = map;
		$window.searchBox = searchBox;
	}
});

ctsApp.controller('search_options', function($scope){

    var controPanel = getById("control_panel");

    $scope.openCoordSearch = function(){
        controPanel.style.width = "350px";
        controPanel.style.height = "40%";
        getById('coordinate_search').style.display="block";
    }

    $scope.openPlaceSearch = function(){
        controPanel.style.width = "350px";
        controPanel.style.height = "30%";
        getById('place_search').style.display="block";
    }

    $scope.openMarkerSearch = function(){
        controPanel.style.width = "350px";
        controPanel.style.height = "40%";
        getById('marker_search').style.display="block";
    }

    $scope.closePanel = function(){
        controPanel.style.width = "0";
        var controlPanels = document.getElementsByClassName('controlPanel');
        for(var i=0; i<controlPanels.length; i++){
            controlPanels[i].style.display="none";
        }
    }

});

ctsApp.controller('countries', function($scope){

});