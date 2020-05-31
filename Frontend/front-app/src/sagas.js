import {accountsSagas} from './modules/accounts/sagas/accountsSagas';
import {signInSagas} from './modules/login/sagas/authenticationSagas';
import {signUpSagas} from './modules/registration/sagas/registrationSagas';
import {saveAuthSagas, removeAuthSagas} from './modules/app/sagas/appSagas';
import {all } from 'redux-saga/effects';

function* rootSaga(){
    yield all([
        saveAuthSagas(),
        removeAuthSagas(),

        signInSagas(),  // authentication
        signUpSagas(),  // registration
        
        accountsSagas(),
        

    ]);
}
export default rootSaga;