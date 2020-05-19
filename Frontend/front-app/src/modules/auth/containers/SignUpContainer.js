import React from "react";
import {Link, Redirect} from "react-router-dom";
import {connect} from 'react-redux';
import {postSignUpRequest, postSignUpError} from '../actions/authActions'
import '../../../App.css';

class SignUpContainer extends React.Component{
    
    onSubmit = (event) => {
        event.preventDefault();
        
        let username = event.target.username.value;
        let email = event.target.email.value;
        let password = event.target.password1.value;
        let password2 = event.target.password2.value;

        const data = {
            username,
            email, 
            password
        };

        console.log(username + " len "+username.length);

        if (username.length < 3){
            this.props.dispatch(postSignUpError({errorMessage:'Слишком короткое имя'}));
            return;
        }
        
        if (password !== password2 ){
            this.props.dispatch(postSignUpError({errorMessage:'Пароли не совпадают'}));
            return;
        }

        this.props.dispatch(postSignUpRequest(data));
    }
    
    render(){

        console.log('up props '+JSON.stringify(this.props));

        const{ authorized, 
            errorMessage, 
            loading, 
            isSuccess} = this.props;

        if (authorized){
            return(<Redirect to="/private" />);
        }

        if (isSuccess){
            return(<Redirect to="/signin" />);
        }

        if (loading){
            return (<div>Loading...</div>);
        }

        

        return (
            <div>
                <h1>Регистрация</h1>

                { errorMessage ?  <div className="error-message">{errorMessage}</div> :<div/>}
                
                <form onSubmit={this.onSubmit}>
                    <input type='text' 
                        placeholder='Имя' 
                        id='username'
                    />
                     <br/>
                    <br/>
                    <input type='text' 
                        id='email' 
                        placeholder='Email'
                    />
                    <br/>
                    <br/>
                    <input type='password' 
                        id='password1' 
                        placeholder='Пароль'
                    />
                    <br/>
                    <br/>
                    <input type='password' 
                        id='password2' 
                        placeholder='Повторите пароль'
                    />
                    <br/>
                    <button>Зарегистрироваться</button>
                </form>

                <br/>
                <Link to='/signin'>Есть аккаунт</Link>
            </div>
        );
    }
};

const mapStateToProps = state =>({
    loading: state.signUp.loading,
    errorMessage: state.signUp.errorMessage,
    isSuccess: state.signUp.isSuccess,
});

export default connect(mapStateToProps)(SignUpContainer);