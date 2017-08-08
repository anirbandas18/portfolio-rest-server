portfolio.controller('InfoController', function($scope, $window) {
	$scope.alert = function() {
		$window.alert('hi');
		console.log('hi');
	}
});