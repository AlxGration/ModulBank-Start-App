import React from 'react';
import {BrowserRouter, Route, Switch, Redirect} from "react-router-dom";
import Home from "../../home/containers/Home";
import SignInContainer from "../../auth/containers/SignInContainer";
import SignUpContainer from "../../auth/containers/SignUpContainer";
import {PrivateRoute} from "../components/PrivateRoute";
import {connect} from 'react-redux';


export class App extends React.Component{

  render(){

    console.log('App props: '+JSON.stringify(this.props));

    const authorized = this.props.authorized;
    
    
    return(
      <BrowserRouter>
        <div>
          <Switch>
            <PrivateRoute exact path="/private" authorized={authorized}>
              <Home/>
            </PrivateRoute>

            <Route exact path="/signin" >
              <SignInContainer authorized={authorized} />
            </Route>

            <Route exact path="/signup" >
              <SignUpContainer authorized={authorized}/> 
            </Route>

            {
              authorized ?
                <Redirect to="/private"/>:
                <Redirect to="/signin"/>
            }

          </Switch>
        </div>
      </BrowserRouter>
    );

  }
}

const mapStateToProps = (state) => ({
  authorized: state.app.authorized,
});

export default connect(mapStateToProps)(App);;
