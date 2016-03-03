<?php

namespace App\Helpers;

use JWTAuth;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Privileges
 *
 * @author wdmtraining
 */
class AuthenticatedUser {

    private $role = null;
    private $privileges = null;

    public function __construct() {

        $this->initialize();
    }

    public function hasRole($r) {
        return $this->role === $r;
    }

    public function hasPrivilege($p) {
        return in_array($p, $this->privileges);
    }

    private function initialize() {

        // ----------------------------------------------
        // - retrieve authenticated user 
        // ----------------------------------------------
        $user = JWTAuth::parseToken()->authenticate();
        $role_id = $user->role_id;

        // ----------------------------------------------
        // - retireve the authenticated user role from the DB
        // ----------------------------------------------
        $roles = \DB::table('roles')
                ->select('role_name')
                ->where('roles.id', '=', $role_id)
                ->get();

        $this->role = $roles[0]->role_name;

        // ----------------------------------------------
        // - retireve the authenticated user privileges from the DB
        // ----------------------------------------------
        $privileges = \DB::table('privileges')
                ->select('privilege_name')
                ->join('roles_have_privileges', 'roles_have_privileges.privilege_id', '=', 'privileges.id')
                ->where('roles_have_privileges.role_id', '=', $role_id)
                ->get();

        $this->privileges = array();
        foreach ($privileges as $privilege) {
            array_push($this->privileges, $privilege->privilege_name);
        }

        // ----------------------------------------------
        \Log::debug("AuthenticatedUserRoles : ");
        \Log::debug('Authenticated user role Id = ' . print_r($role_id, true));
        // \Log::debug(print_r($user, true));
        \Log::debug('Authenticated user role = ' . print_r($this->role, true));
        \Log::debug('Authenticated user privileges = ' . print_r($this->privileges, true));
        // ----------------------------------------------
    }

}
