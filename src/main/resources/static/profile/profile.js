angular.module('market').controller('ProfileController',
    function ($scope, $http, $rootScope, $location) {
        contextPath = 'http://localhost:8089/market/api/v1';

        $scope.loadProfile = function () {
            $http.get(contextPath + "/profiles")
                .then(function successCallback(response) {
                    $scope.user = response.data;
                }, function errorCallback(response) {
                    alert("UNAUTHORIZED");
                });
        };

        $scope.loadOrders = function () {
            $http.get(contextPath + "/orders")
                .then(function (response) {
                    $scope.orders = response.data;
                });
        };

        if ($rootScope.isUserLoggedIn()) {
            $scope.loadProfile();
            $scope.loadOrders();
        } else {
            alert("Необходимо войти в свою учетную запись");
            $location.path("/")
        }
    }
);