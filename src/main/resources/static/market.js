angular.module('market', ['ngStorage']).controller('MarketController',
        function ($rootScope, $scope, $localStorage, $http) {

    const contextPath = 'http://localhost:8089/market/api/v1'

    if ($localStorage.marketUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.marketUser.token;
    }

    $scope.loadProducts = function() {
        $http({
            url: contextPath + "/products",
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
        $http.delete(contextPath + "/products/" + id)
            .then(function() {
                $scope.loadProducts()
            });
    };

    $scope.addProduct = function() {
        $http.post(contextPath + "/products", $scope.newProduct)
            .then(function(response) {
                $scope.loadProducts();
            });
    };

    $scope.loadCart = function() {
        $http.get(contextPath + "/carts")
            .then(function(response) {
                $scope.cart = response.data;
            });
    };

    $scope.addToCart = function(id) {
        $http.get(contextPath + "/carts/" + id)
            .then(function(response) {
                $scope.loadCart();
            });
    };

    $scope.deleteRecord = function(id, delta) {
        $http({
            method: 'DELETE',
            url: contextPath + "/carts/" + id,
            params: {
                d: delta
            }
        }).then(function() {
            $scope.loadCart();
        });
    };

    $rootScope.isUserLoggedIn = function() {
        if ($localStorage.marketUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.tryToAuth = function() {
        $http.post(contextPath + "/auth", $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.marketUser = {
                        username: $scope.user.username,
                        token: response.data.token
                    };
                    $scope.user.username = null;
                    $scope.user.token = null;
                }
            }, function errorCallback(response) {

            });
    };

    $scope.tryToLogout = function() {
        $scope.clearUser();
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
    };

    $scope.clearUser = function() {
        $http.defaults.headers.common.Authorization = '';
        delete $localStorage.marketUser;
    };

    $scope.loadProducts();
    $scope.loadCart();

});