import {accountsSagas} from '../../accounts/sagas/accountsSagas';
import {signInSagas} from '../../auth/sagas/authenticationSagas';
import {signUpSagas} from '../../auth/sagas/registrationSagas';
import {saveAuthSagas, removeAuthSagas} from './appSagas';
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