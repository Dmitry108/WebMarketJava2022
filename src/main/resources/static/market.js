angular.module('market', []).controller('MarketController', function ($scope, $http) {
    const contextPath = 'http://localhost:8089/market'

    $scope.loadProducts = function() {
        $http.get(contextPath + '/products')
            .then(function (response) {
                $scope.products = response.data;
            });
    };
    $scope.deleteProduct = function(id) {
        $http.get(contextPath + '/products/delete/' + id)
            .then(function() {
                $scope.loadProducts()
                }
            );
    };
    $scope.loadProducts();
});