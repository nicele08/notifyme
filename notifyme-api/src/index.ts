import express from 'express';
import cors from 'cors';
import keys from './app/constants/keys';
import appRouter from './app/routes';

const port = keys.PORT;
const app = express();

app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

app.use(appRouter);

app.get('/', (req, res) => {
  res.send('Welcome to NotifyMe API! Mail, SMS, and Push Notification');
});

app.listen(port, () => {
  process.stdout.write(`🚀 http://localhost:${port} \n`);
});
