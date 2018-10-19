import * as functions from 'firebase-functions';
import * as admin from "firebase-admin";
admin.initializeApp();
// // Start writing Fire base Functions
// // https://firebase.google.com/docs/functions/typescript
//
export const sendFcm = functions.https.onCall((data, context) => {
    admin.messaging().send(data).then(value => {
        console.log('Successfully sent : ', value);
    }).catch(reason => {
        console.log('Error sending : ', reason);
    })
});
