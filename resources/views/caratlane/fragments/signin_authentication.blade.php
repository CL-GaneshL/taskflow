
<!-- ================================================== -->
<!-- -nav bar header                                    -->
<!-- ================================================== -->  
<header class="navbar-header">

    <a class="navbar-brand"">
        <img ng-src="<%app.layout.logo%>" /> 
    </a>

</header>

<!-- ================================================== -->
<!-- - page headers                                     -->
<!-- ================================================== -->
<section id="page-title">
    <div class="row">

        <!-- this section is empty but allows to preserve the layout -->

    </div>
</section>


<!-- ================================================== -->
<!-- - spinner                                          -->
<!-- ================================================== -->
<span 
    us-spinner="{radius:30, width:8, length: 16}" 
    spinner-key="signinSpinner"
    spinner-start-active="true">

</span> 


<!-- ================================================== -->
<!-- - page body                                        -->
<!-- ================================================== -->
<div class="row">

    <h3 class="center padding-25">User Authentication ...</h3>

</div>

<!-- ================================================== -->
<!-- - kick off the authentication controller           -->
<!-- ================================================== -->
<div ng-controller="authenticationCtrl">        </div>

