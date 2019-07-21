

countryApp.controller('feed_list', function($scope, $http, feeds){
    
    $scope.getFeeds = function(){
        $http.get('/cts-country/transit-feed/getFeeds').then(function(response){
            feeds = response['data'];
        });
    }

    $scope.getAllFeeds = function(){
        return feeds;
    };
});