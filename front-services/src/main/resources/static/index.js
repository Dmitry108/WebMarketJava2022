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
            try {
                let jwt = $localStorage.marketUser.token;
                let payload = JSON.parse(atob(jwt.split('.')[1]));
                let currentTime = parseInt(new Date().getTime() / 1000);
                if (currentTime > payload.exp) {
                    console.log("Token is expired!");
                    $http.defaults.headers.common.Authorization = '';
                    delete $localStorage.marketUser;
                }
            } catch (e) {}
        }
        if ($localStorage.marketUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.marketUser.token;
        }
        if (!$localStorage.guestCartKey) {
            $http.get('http://localhost:8888/cart/api/v1/cart/generate')
            .then(function (response) {
                $localStorage.guestCartKey = response.data.value;
            });
        }
    }
})();

angular.module('market').controller('IndexController',
    function ($rootScope, $scope, $http, $location, $localStorage) {

        const contextPath = 'http://localhost:8888'

        $scope.tryToAuth = function () {
            $http.post(contextPath + "/auth/api/v1/auth", $scope.user)
                .then(function successCallback(response) {
                    if (response.data.token) {
                        $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                        $localStorage.marketUser = {
                            username: $scope.user.username,
                            token: response.data.token
                        };
                        console.log($scope.user.username);
                        //смержить готевую корзину и нормальную
                        $http.get(contextPath + "/cart/api/v1/cart/" + $localStorage.guestCartKey + "/merge");
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