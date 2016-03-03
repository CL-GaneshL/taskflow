<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Employee extends Model {

    // ----------------------------------
    // - fillable attributs
    // ----------------------------------
    protected $fillable = [
        'id',
        'employeeId',
        'firstName',
        'lastName',
        'fullName',
        'avatar',
        'location',
        'email',
        'phone',
        'productivity',
        'isProjectManager',
        'isTeamLeader',
        'employementType'
    ];
    // ----------------------------------
    // - remove timestamps
    // ----------------------------------
    public $timestamps = false;

    // ----------------------------------
    // - mutators
    // ----------------------------------
//    public function setEmployeeIdAttribute($employeeId) {
//        $this->attributes['employeeId'] = trim($employeeId) !== '' ? $employeeId : null;
//    }
//
//    public function setFirstNameAttribute($firstName) {
//        $this->attributes['firstName'] = trim($firstName) !== '' ? $firstName : null;
//    }
//
//    public function setLastNameAttribute($lastName) {
//        $this->attributes['lastName'] = trim($lastName) !== '' ? $lastName : null;
//    }
//
//    public function setFullNameAttribute($fullName) {
//        $this->attributes['fullName'] = trim($fullName) !== '' ? $fullName : null;
//    }
//
//    public function setAvatarAttribute($avatar) {
//        $this->attributes['avatar'] = trim($avatar) !== '' ? $avatar : null;
//    }
//
//    public function setLocationAttribute($location) {
//        $this->attributes['location'] = trim($location) !== '' ? $location : null;
//    }
//
//    public function setEmailAttribute($email) {
//        $this->attributes['email'] = trim($email) !== '' ? $email : null;
//    }
//
//    public function setPhoneAttribute($phone) {
//        $this->attributes['phone'] = trim($phone) !== '' ? $phone : null;
//    }
//
//    public function setProductivityAttribute($productivity) {
//        $this->attributes['productivity'] = trim($productivity) !== '' ? $productivity : null;
//    }
//
//    public function setIisProjectManagerAttribute($isProjectManager) {
//        $this->attributes['isProjectManager'] = trim($isProjectManager) !== '' ? $isProjectManager : null;
//    }
//
//    public function setIsTeamLeaderAttribute($isTeamLeader) {
//        $this->attributes['isTeamLeader'] = trim($isTeamLeader) !== '' ? $isTeamLeader : null;
//    }
//
//    public function setEmployementTypeAttribute($employementType) {
//        $this->attributes['employementType'] = trim($employementType) !== '' ? $employementType : null;
//    }
}
