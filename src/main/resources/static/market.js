angular.module('market', []).controller('MarketController', function ($scope, $http) {
    const contextPath = 'http://localhost:8089/market/api/v1/products'

    $scope.loadProducts = function() {
        console.log("path: " + contextPath);
        $http({
            url: contextPath,
            method: 'GET',
            params: {
                p: $scope.page,
                min_price: $scope.f ? $scope.f.min : null,
                max_price: $scope.f ? $scope.f.max : null
            }
        }).then(function(response) {
            $scope.products = response.data.content;
        });
    };

    $scope.deleteProduct = function(id) {
        $http.delete(contextPath + "/" + id)
            .then(function() {
                $scope.loadProducts()
            });
    };

   $scope.addProduct = function() {
        $http.post(contextPath, $scope.newProduct)
            .then(function(response) {
            console.log("удалось");
                $scope.loadProducts();
            });
   };

   $scope.loadProducts();
});