angular.module('market', []).controller('MarketController', function ($scope, $http) {
    const contextPath = 'http://localhost:8089/market'

    $scope.loadProducts = function() {
        $http.get(contextPath + '/products')
            .then(function (response) {
                $scope.products = response.data;
            });
    };
    $scope.changeCost = function(id, delta) {
            $http({
                url: contextPath + '/change_cost',
                method: 'GET',
                params: {
                    id: id,
                    delta: delta
                }}

            ).then(function(response) {
                $scope.loadProducts();

            });
        };
    $scope.loadProducts();
});