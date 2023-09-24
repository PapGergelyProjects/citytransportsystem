var circleStorage = [];
var markerStorage = [];
var stopMarkerStorage = [];

locationSelector.service('setLocation', function(stopCoordinatesAssembler){
	var instance = this;
	this.setCoordinates = function(coordinate, stopCoordinates, r){
		let newCoord = new google.maps.LatLng(coordinate['latitude'], coordinate['longitude']);
		map.setCenter(newCoord);
		map.setZoom(16);
		
		instance.clearMarkers();
		let coordMarker = new google.maps.Marker({
			position:{lat:coordinate['latitude'], lng:coordinate['longitude']},
			map: map,
			title: 'Center'
		});
		markerStorage.push(coordMarker);
		stopCoordinatesAssembler.showStopCoordinates(stopCoordinates);
		instance.addCircle(coordinate['latitude'], coordinate['longitude'], r);
	}
	
	this.addCircle = function(latitude, longnitude, r){
		if(circleStorage.length>0){
			for(let i=0; i<circleStorage.length; i++){
				circleStorage[i].setMap(null);
			}
		}
		let circle = new google.maps.Circle({
			strokeColor: '#0040ff',
			map: map,
			center: {lat:latitude, lng:longnitude},
			fillOpacity: 0.1,
			radius: r
		});
		circleStorage.push(circle);
	}
	
	this.clearMarkers = function(){
		if(0<markerStorage.length){
			for(let i=0; i<markerStorage.length; i++){
				markerStorage[i].setMap(null);
			}
		}
		if(0<stopMarkerStorage.length){
			for(let i=0; i<stopMarkerStorage.length; i++){
				stopMarkerStorage[i].setMap(null);
			}
		}
	}
});

locationSelector.service('click_radius', function($http, setLocation){
	this.addClickLst = function(event, $rootScope){
		if(getById('points').checked){
			let latitude = event.latLng.lat();
			let longitude = event.latLng.lng();
			let radius = Number(getById('rad').value);
			let json = {radius:radius, searchCoordinate:{latitude:latitude, longitude:longitude}}
			let service = getById('times').checked ? $http.post('/nuts/radius/stop_times', json) : $http.post('/nuts/radius/stop_location', json);
			$rootScope.loading = true;
			service.then(function(reponse){
				setLocation.setCoordinates(json['searchCoordinate'], reponse['data'], radius);
				$rootScope.loading = false;
			}, function(reponse){
				console.log(reponse['data']);//error
			});
		}
	}
});