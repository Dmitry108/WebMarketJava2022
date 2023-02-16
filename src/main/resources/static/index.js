(function () {
    angular
        .module('market', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'home/home.html',
                controller: 'HomeController'
            })
            .when('/store', {
                templateUrl: 'store/store.html',
                controller: 'StoreController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'CartController'
            })
            .when('/profile', {
                templateUrl: 'profile/profile.html',
                controller: 'ProfileController'
            })
            .otherwise({
                redirectTo: '/store'
            });
    }

    function run($rootScope, $localStorage, $http) {
        if ($localStorage.marketUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.marketUser.token;
        }
    }
})();

angular.module('market').controller('IndexController',
    function ($rootScope, $scope, $http, $location, $localStorage) {

        const contextPath = 'http://localhost:8089/market/api/v1'

        $scope.tryToAuth = function () {
            $http.post(contextPath + "/auth", $scope.user)
                .then(function successCallback(response) {
                    if (response.data.token) {
                        $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                        $localStorage.marketUser = {
                            username: $scope.user.username,
                            token: response.data.token
                        };
                        $scope.user.username = null;
                        $scope.user.password = null;
                    }
                }, function errorCallback(response) {

                });
        };

        $scope.tryToLogout = function () {
            $scope.clearUser();
            $scope.user = null;
            $location.path('/');
        };

        $scope.clearUser = function () {
            $http.defaults.headers.common.Authorization = '';
            delete $localStorage.marketUser;
        };

        $rootScope.isUserLoggedIn = function () {
            if ($localStorage.marketUser) {
                return true;
            } else {
                return false;
            }
        };
    });