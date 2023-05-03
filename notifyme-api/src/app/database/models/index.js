"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.connection = void 0;
const fs_1 = __importDefault(require("fs"));
const path_1 = __importDefault(require("path"));
const sequelize_1 = require("sequelize");
const basename = path_1.default.basename(__filename);
const env = process.env.NODE_ENV || 'development';
const config = require(`${__dirname}/../config/db.config.js`)[env];
const db = {};
let sequelize;
if (config.use_env_variable) {
    sequelize = new sequelize_1.Sequelize(process.env[config.use_env_variable], config);
}
else {
    sequelize = new sequelize_1.Sequelize(config.database, config.username, config.password, config);
}
sequelize
    .authenticate()
    .then(() => {
    console.log('✅ Database connection has been established successfully.');
})
    .catch(error => {
    console.error('❌ Unable to connect to the database:', error.message);
});
fs_1.default.readdirSync(`${__dirname}/definitions`)
    .filter(file => file.indexOf('.') !== 0 &&
    file !== basename &&
    file.slice(-3) === '.js')
    .forEach(file => {
    const model = require(path_1.default.join(`${__dirname}/definitions`, file))(sequelize, sequelize_1.DataTypes);
    db[model.name] = model;
});
Object.keys(db).forEach(modelName => {
    if (typeof db[modelName].associate === 'function') {
        db[modelName].associate(db);
    }
});
exports.connection = sequelize;
exports.default = db;
//# sourceMappingURL=index.js.map