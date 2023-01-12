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
            });
    };

   $scope.addProduct = function() {
        $http.post(contextPath + '/products', $scope.newProduct)
            .then(function(response) {
                $scope.loadProducts();
            });
   };

   $scope.getProductsByLowPrice = function() {
       $http({
            url: contextPath + '/products/low_price',
            method: 'get',
            params: {
                limit: $scope.max
            }
       }).then(function(response) {
            $scope.products = response.data;
       });
   };

   $scope.getProductsByHighPrice = function() {
        $http({
            url: contextPath + '/products/high_price',
            method: 'get',
            params: {
                limit: $scope.min
            }
        }).then(function(response) {
            $scope.products = response.data;
        });
   };

   $scope.getProductsByPriceBetween = function() {
        $http({
            url: contextPath + '/products/between',
            method: 'get',
            params: {
                min: $scope.min,
                max: $scope.max
            }
        }).then(function(response) {
            $scope.products = response.data;
        });
   };

   $scope.getProductsByPrice = function() {
        if ($scope.max != null && $scope.min != null) {
            $scope.getProductsByPriceBetween();
        } else if ($scope.max != null) {
            $scope.getProductsByLowPrice()
        } else if ($scope.min != null) {
            $scope.getProductsByHighPrice();
        }
   };

   $scope.loadProducts();
});