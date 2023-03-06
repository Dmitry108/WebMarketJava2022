angular.module('market').controller('CartController',
    function ($rootScope, $scope, $localStorage, $http) {

        const contextPath = 'http://localhost:8888/cart/api/v1'

        $scope.loadCart = function () {
            $http.get(contextPath + "/cart/" + $localStorage.guestCartKey)
                .then(function (response) {
                    $scope.cart = response.data;
                });
        };

        $scope.addToCart = function (id) {
            $http.get(contextPath + "/cart/" + $localStorage.guestCartKey + "/add/" + id)
                .then(function (response) {
                    $scope.loadCart();
                });
        };

        $scope.decreaseProductInCart = function (id) {
            $http.get(contextPath + "/cart/" + $localStorage.guestCartKey + "/decrease/" + id)
                .then(function () {
                    $scope.loadCart();
                });
        };

        $scope.deleteRecord = function (id) {
            $http.get(contextPath + "/cart/" + $localStorage.guestCartKey + "/delete/" + id)
                .then(function () {
                    $scope.loadCart();
                });
        };

        $scope.clearCart = function () {
            $http.get(contextPath + "/cart/" + $localStorage.guestCartKey + "/clear")
                .then(function (response) {
                    $scope.loadCart();
                });
        };

        $scope.createOrder = function () {
            $http.post("http://localhost:8888/core/api/v1/orders", $scope.orderDetails)
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