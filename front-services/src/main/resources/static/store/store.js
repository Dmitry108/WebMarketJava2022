angular.module('market').controller('StoreController',
    function ($scope, $http, $localStorage) {

        const contextPath = 'http://localhost:8888/core/api/v1'

        $scope.loadProducts = function (page = 1) {
            $http({
                url: contextPath + "/products",
                method: 'GET',
                params: {
                    p: page,
                    min_price: $scope.f ? $scope.f.min : null,
                    max_price: $scope.f ? $scope.f.max : null
                }
            }).then(function (response) {
                $scope.productsPage = response.data;
                $scope.paginationArray = $scope.generatePageIndices(1, $scope.productsPage.totalPages);
            });
        };

        $scope.generatePageIndices = function (start, end) {
            let indices = [];
            for (let i = start; i <= end; i++) {
                indices.push(i);
            }
            return indices;
        }

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
            //
            $http.get("http://localhost:8888/cart/api/v1/cart/" + $localStorage.guestCartKey + "/add/" + id)
                .then(function (response) {
                });
        };

        $scope.loadProducts();
    }
);