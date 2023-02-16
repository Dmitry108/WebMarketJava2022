angular.module('market').controller('CartController',
    function ($rootScope, $scope, $localStorage, $http) {

        const contextPath = 'http://localhost:8089/market/api/v1'

        $scope.loadCart = function () {
            $http.get(contextPath + "/carts")
                .then(function (response) {
                    $scope.cart = response.data;
                });
        };

        $scope.addToCart = function (id) {
            $http.get(contextPath + "/carts/add/" + id)
                .then(function (response) {
                    $scope.loadCart();
                });
        };

        $scope.decreaseProductInCart = function (id) {
            $http.get(contextPath + "/carts/decrease/" + id)
                .then(function () {
                    $scope.loadCart();
                });
        };

        $scope.deleteRecord = function (id) {
            $http.get(contextPath + "/carts/delete/" + id)
                .then(function () {
                    $scope.loadCart();
                });
        };

        $scope.clearCart = function () {
            $http.get(contextPath + "/carts/clear")
                .then(function (response) {
                    $scope.loadCart();
                });
        };

        $scope.createOrder = function () {
            $http.post(contextPath + "/orders", $scope.orderDetails)
                .then(function () {
                    $scope.clearCart();
                    $scope.orderDetails = null;
                });
        };

        $scope.disabledCheckOut = function () {
            alert('Для оформления заказа необходимо войти в свою учетную запись')
        };

        $scope.loadCart();
    });