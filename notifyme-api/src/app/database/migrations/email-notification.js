/** @type {import('sequelize-cli').Migration} */
module.exports = {
  async up(queryInterface, Sequelize) {
    await queryInterface.createTable('tbl_email_notifications', {
      id: {
        type: Sequelize.BIGINT,
        allowNull: false,
        primaryKey: true,
        autoIncrement: true,
      },
      ownerId: {
        type: Sequelize.BIGINT,
        allowNull: false,
      },
      fromName: {
        type: Sequelize.STRING,
        unique: true,
      },
      fromEmail: {
        type: Sequelize.STRING,
      },
      to: {
        type: Sequelize.ARRAY(Sequelize.STRING),
        allowNull: false,
      },
      subject: {
        type: Sequelize.STRING,
      },
      text: {
        type: Sequelize.TEXT,
      },
      html: {
        type: Sequelize.TEXT,
      },
      status: {
        type: Sequelize.ENUM('pending', 'sent', 'failed'),
        defaultValue: 'pending',
        comment: 'pending, sent, failed',
      },
      createdAt: {
        allowNull: false,
        type: Sequelize.DATE,
        default: new Date(),
      },
      updatedAt: {
        allowNull: false,
        type: Sequelize.DATE,
        default: new Date(),
      },
    });
  },
  async down(queryInterface, Sequelize) {
    await queryInterface.dropTable('tbl_email_notifications');
  },
};
