angular.module('market').controller('StoreController',
    function ($scope, $http) {

        const contextPath = 'http://localhost:8089/market/api/v1'

        $scope.loadProducts = function () {
            $http({
                url: contextPath + "/products",
                method: 'GET',
                params: {
                    p: $scope.page,
                    min_price: $scope.f ? $scope.f.min : null,
                    max_price: $scope.f ? $scope.f.max : null
                }
            }).then(function (response) {
                console.log(response.data.content)
                $scope.products = response.data.content;
            });
        };

        $scope.deleteProduct = function (id) {
            $http.delete(contextPath + "/products/" + id)
                .then(function () {
                    $scope.loadProducts()
                });
        };

        $scope.addProduct = function () {
            $http.post(contextPath + "/products", $scope.newProduct)
                .then(function (response) {
                    $scope.loadProducts();
                });
        };

        $scope.addToCart = function (id) {
            $http.get(contextPath + "/carts/add/" + id)
                .then(function (response) {
                });
        };

        $scope.loadProducts();
    }
);