<?php

namespace App\Http\Controllers;

use Config;
use JWTAuth;
use App\User;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use Tymon\JWTAuth\Exceptions\JWTException;

class AuthenticateController extends Controller {

    private $HTTP_OK = null;
    private $HTTP_INVALID_CREDENTIALS_ERROR = null;
    private $HTTP_INVALID_CREDENTIALS_ERROR_MSG = null;
    private $HTTP_INTERNAL_SERVER_ERROR = null;
    private $HTTP_INTERNAL_SERVER_ERROR_MSG = null;

    public function __construct() {

        $this->HTTP_OK = Config::get('caratlane.dbconstants.HTTP_OK');
        $this->HTTP_INVALID_CREDENTIALS_ERROR = Config::get('caratlane.dbconstants.HTTP_INVALID_CREDENTIALS_ERROR');
        $this->HTTP_INVALID_CREDENTIALS_ERROR_MSG = Config::get('caratlane.dbconstants.HTTP_INVALID_CREDENTIALS_ERROR_MSG');
        $this->HTTP_INTERNAL_SERVER_ERROR = Config::get('caratlane.dbconstants.HTTP_INTERNAL_SERVER_ERROR');
        $this->HTTP_INTERNAL_SERVER_ERROR_MSG = Config::get('caratlane.dbconstants.HTTP_INTERNAL_SERVER_ERROR_MSG');

        // -------------------------------------
        // Apply the jwt.auth middleware to all methods in this controller
        // except for the authenticate method. We don't want to prevent
        // the user from retrieving their token if they don't already have it
        // -------------------------------------
        $this->middleware('jwt.auth', ['except' => ['authenticate']]);
    }

    /**
     * 
     * @return type
     */
    public function index() {

        // -------------------------------------
        // - Retrieve all the users
        // -------------------------------------
        $users = User::all();
        return $users;
    }

    /**
     * 
     * @param Request $request
     * @return type
     */
    public function authenticate(Request $request) {

        $credentials = $request->only('username', 'password');

        // ---------------------------------------------------
        \Log::debug('authenticate : $credentials = ' . print_r($credentials, true));
        // ---------------------------------------------------

        try {
            // verify the credentials and create a token for the user
            if (!$token = JWTAuth::attempt($credentials)) {
                return response()->json(
                                [
                            'message' => $this->HTTP_INVALID_CREDENTIALS_ERROR_MSG
                                ], $this->HTTP_INVALID_CREDENTIALS_ERROR
                );
            }
        } catch (JWTException $e) {
            // something went wrong
            return response()->json(
                            [
                        'message' => $this->HTTP_INTERNAL_SERVER_ERROR_MSG
                            ], $this->HTTP_INTERNAL_SERVER_ERROR
            );
        }

        // ---------------------------------------------------
        \Log::debug('authenticate : $token = ' . print_r($token, true));
        // ---------------------------------------------------
        // if no errors are encountered we can return a JWT
        return response()->json(compact('token'));
    }

    /**
     * 
     * @return type
     */
    public function getAuthenticatedUser() {

        try {

            if (!$user = JWTAuth::parseToken()->authenticate()) {
                return response()->json(['user_not_found'], 404);
            }
        } catch (Tymon\JWTAuth\Exceptions\TokenExpiredException $e) {

            return response()->json(['token_expired'], $e->getStatusCode());
        } catch (Tymon\JWTAuth\Exceptions\TokenInvalidException $e) {

            return response()->json(['token_invalid'], $e->getStatusCode());
        } catch (Tymon\JWTAuth\Exceptions\JWTException $e) {

            return response()->json(['token_absent'], $e->getStatusCode());
        }

        // ---------------------------------------------------
        // \Log::debug('authenticate : $user = ' . print_r($user, true));
        // ---------------------------------------------------
        // the token is valid and we have found the user via the sub claim
        return response()->json(compact('user'));
    }

    /**
     * 
     * @param Request $request
     */
    public function logout(Request $request) {

        // ---------------------------------------------------
        \Log::debug('logout : logging out ... ');
        // ---------------------------------------------------

        $token = JWTAuth::getToken();
        JWTAuth::invalidate($token);
    }

}
