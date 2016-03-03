<?php

namespace App\Providers;

use App\Helpers\AuthenticatedUser;
use Illuminate\Support\ServiceProvider;

class AuthenticatedUserProvider extends ServiceProvider {

    /**
     * Bootstrap the application services.
     *
     * @return void
     */
    public function boot() {
        //
    }

    /**
     * Register the application services.
     *
     * @return void
     */
    public function register() {

        $this->app->bind('App\Helpers\AuthenticatedUser', function() {
            return new AuthenticatedUser();
        }
        );
    }

}
