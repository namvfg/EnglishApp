const Profile = () => {
    const avatar = localStorage.getItem("avatar");
    const email = localStorage.getItem("email");
    const role = localStorage.getItem("role");
    const firstName = localStorage.getItem("firstName");
    const lastName = localStorage.getItem("lastName");

    return (
        <div className="container mt-5 text-center">
            <h3>Thông tin cá nhân</h3>
            {avatar && (
                <img
                    src={avatar}
                    alt="Avatar"
                    style={{
                        width: "120px",
                        height: "120px",
                        objectFit: "cover",
                        borderRadius: "50%",
                        marginBottom: "1rem"
                    }}
                />
            )}
            <p><strong>Họ:</strong> {firstName}</p>
            <p><strong>Tên:</strong> {lastName}</p>
            <p><strong>Email:</strong> {email}</p>
            <p><strong>Vai trò:</strong> {role}</p>
        </div>
    );
};

export default Profile;