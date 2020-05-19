import {put, call, takeEvery} from 'redux-saga/effects';
import {LOGIN_SAVE_REQUEST, LOGOUT_REQUEST, loginSaveSuccess, logOutSuccess} from '../actions/appActions';
import {saveAuth, removeAuth} from '../api/authData';

function* logIn(payload) {
 
    // сохранение токена и айди
    yield call(saveAuth, payload);
    yield put(loginSaveSuccess());
  
}
function* saveAuthSagas(payload){
    yield takeEvery(LOGIN_SAVE_REQUEST, logIn);
}
export { saveAuthSagas };
//------------------------------------------------------------------------
//
//------------------------------------------------------------------------
function* logOut() {
 
    // удаление токена и айди
    yield call(removeAuth);
    yield put(logOutSuccess());
  
}
function* removeAuthSagas(){
    yield takeEvery(LOGOUT_REQUEST, logOut);
}
export { removeAuthSagas };