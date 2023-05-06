/**
 * @typedef {import('sequelize').DataTypes} DataTypes
 * @typedef {import('sequelize').Sequelize} Sequelize
 */

/**
 * @param {Sequelize} sequelize
 * @param {DataTypes} DataTypes
 */
module.exports = (sequelize, DataTypes) => {
  const EmailNotification = sequelize.define(
    'EmailNotification',
    {
      id: {
        type: DataTypes.BIGINT,
        allowNull: false,
        primaryKey: true,
        autoIncrement: true,
      },
      ownerId: {
        type: DataTypes.BIGINT,
        allowNull: false,
      },
      fromName: {
        type: DataTypes.STRING,
        unique: true,
      },
      fromEmail: {
        type: DataTypes.STRING,
      },
      to: {
        type: DataTypes.ARRAY(DataTypes.STRING),
        allowNull: false,
      },
      subject: {
        type: DataTypes.STRING,
      },
      text: {
        type: DataTypes.TEXT,
      },
      html: {
        type: DataTypes.TEXT,
      },
      status: {
        type: DataTypes.ENUM('pending', 'sent', 'failed'),
        defaultValue: 'pending',
        comment: 'pending, sent, failed',
      },
    },
    {
      tableName: 'tbl_email_notifications',
      timestamps: true,
    },
  );

  EmailNotification.associate = models => {
    // associations can be defined here
  };

  return EmailNotification;
};
