var Home = angular.module("Home", []);
Home.controller("pricerange", function($scope){
    $scope.add = function(){
    localStorage.setItem("price_range",$scope.price_range);
    }
    
});
Home.controller("Questionnaire", function($scope, $http, $timeout)
{console.log(localStorage.price_range);    
    $http.get("CategoryServlet?price_range="+localStorage.price_range).success(function(response)
	{
            console.log(response);
            $scope.a = response.questionnaire;
	}).error(function(err){
            console.log(err);
        });    
});
Home.controller("Products", function($scope, $http)
{
	$http.get("./Products.json").success(function(response)
	{
		$scope.a = response.products;
	})
})