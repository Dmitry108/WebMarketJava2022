angular.module('market', []).controller('MarketController', function ($scope, $http) {
    const contextPath = 'http://localhost:8089/market/api/v1'

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
//                console.log(response.data);
//                console.log($scope.cart.totalPrice);
            });
    };

    $scope.addToCart = function(id) {
        $http.get(contextPath + "/carts/add/" + id)
            .then(function(response) {
                $scope.loadCart();
            });
    };

    $scope.decreaseProductInCart = function(id) {
        $http.get(contextPath + "/carts/decrease/" + id)
            .then(function() {
                $scope.loadCart();
            });
    };

    $scope.deleteRecord = function(id) {
        $http.get(contextPath + "/carts/delete/" + id)
            .then(function() {
                $scope.loadCart();
            });
    };

    $scope.clearCart = function() {
        $http.get(contextPath + "/carts/clear")
            .then(function(response) {
                $scope.loadCart();
            });
    };

    $scope.createOrder = function(id) {
        console.log($scope.cart);
        $scope.orderRequest = {userId: id, cart: $scope.cart};
        $http.post(contextPath + "/orders", $scope.orderRequest)
            .then(function() {
                $scope.clearCart();
            });
    };

    $scope.loadProducts();
    $scope.loadCart();
});