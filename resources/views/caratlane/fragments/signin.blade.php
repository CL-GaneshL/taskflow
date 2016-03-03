
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

        <!-- ================================================== -->
        <!-- - page title                                       -->
        <!-- ================================================== -->
        <!-- this section is empty but allows to preserve the layout -->

    </div>
</section>

<!-- ================================================== -->
<!-- - page body                                        -->
<!-- ================================================== -->
<div class="row">
    <div class="main-login col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-2 col-md-4 col-md-offset-4">

        <div ng-controller="signinCtrl">

            <!-- ================================================== -->
            <!-- - login box                                        -->
            <!-- ================================================== -->
            <div class="box-login">
                <form class="form-login">
                    <fieldset>
                        <legend>
                            Sign in to your account
                        </legend>
                        <p>
                            Please enter your Username and Password to log in.
                        </p>

                        <!-- ================================================== -->
                        <!-- - username box                                     -->
                        <!-- ================================================== -->
                        <div class="form-group">
                            <span class="input-icon">
                                <input 
                                    type="text" 
                                    class="form-control" 
                                    ng-model="username"
                                    placeholder="Username"
                                    >
                                <i class="fa fa-user"></i> 
                            </span>
                        </div>

                        <!-- ================================================== -->
                        <!-- - password box                                  -->
                        <!-- ================================================== -->
                        <div class="form-group form-actions">
                            <span class="input-icon">
                                <input 
                                    type="password" 
                                    class="form-control password" 
                                    ng-model="password"
                                    placeholder="Password"
                                    >
                                <i class="fa fa-lock"></i>
                            </span>
                        </div>

                        <!-- ================================================== -->
                        <!-- login button                                     -->
                        <!-- ================================================== -->
                        <div class="form-actions">
                            <button 
                                type="submit"
                                class="btn btn-primary pull-right" 
                                ng-click="login()"
                                >Login
                                <i class="fa fa-arrow-circle-right"></i>
                            </button>
                        </div>

                    </fieldset>
                </form>

                <!-- ================================================== -->
                <!-- - copyright                                           -->
                <!-- ================================================== -->
                <div class="copyright">
                    <%app.year%> &copy; <% app.name %> by <% app.author %>.
                </div>

            </div>

        </div>
    </div>
</div>


