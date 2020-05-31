import React from "react";
import {Link, Redirect} from "react-router-dom";
import {connect} from 'react-redux';
import {postSignUpRequest, postSignUpError} from '../actions/registrationActions'
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
            this.props.postSignUpError({errorMessage:'Слишком короткое имя(<3)'});
            return;
        }

        if ( ! this.validateEmail(email)){
            this.props.postSignUpError({errorMessage:'Некорректный email'});
            return;
        }
        
        if(password.length < 6){
            this.props.postSignUpError({errorMessage:'Слишком короткий пароль(<6)'});
            return;
        }

        if (password !== password2 ){
            this.props.postSignUpError({errorMessage:'Пароли не совпадают'});
            return;
        }

        this.props.postSignUpRequest(data);
    }
    
    validateEmail = (mail) => {
        if ((/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/).test(mail)){
            return (true);
        }
        return (false);
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
            <div className='center-block'>
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
                    <button className="long-button">Зарегистрироваться</button>
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

const mapDispatchProps = {
    postSignUpRequest,
    postSignUpError,
}

export default connect(mapStateToProps, mapDispatchProps)(SignUpContainer);