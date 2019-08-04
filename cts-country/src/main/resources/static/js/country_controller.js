countryApp.controller('feed_list', function($scope, $http, feeds){
    
    $scope.getFeeds = function(){
        $http.get('/cts-country/transit-feed/getFeeds').then(function(response){
        	feeds = response['data'];
        });
    }

    $scope.getAllFeeds = function(){
        return feeds;
    };
    
    $scope.checkTick = function(clickData){
    	let json = {
    		'id':clickData.id,
    		'title':clickData.title,
    		'enabled':clickData.status,
    		'latest':clickData.latest
    	};
    	if(clickData.status){
    		$http.post('/cts-country/register_feed', json).then(function(response){
    			console.log(response);
    		}, function(errorResponse){
    			console.log(errorResponse);
    		});
    	}else{
    		$http.delete('/cts-country/delete_feed/'+json.id).then(function(response){
    			console.log(response);
    		},function(eResponse){
    			console.log(eResponse);
    		});
    	}
    }
});