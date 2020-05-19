import React from "react"
import {Route, Redirect} from "react-router-dom"

class PrivateRoute extends React.Component{
    render(){
        const{children, authorized, ...restProps} = this.props

        return(
            <Route {...restProps}>
                {
                    authorized ? children : <Redirect to="/signin"/>
                }
            </Route>
        );
    }
};

export {PrivateRoute};