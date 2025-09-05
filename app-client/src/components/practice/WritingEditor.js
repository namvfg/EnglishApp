import { useState } from "react";
import { Form, Button } from "react-bootstrap";
import Apis, { endpoints } from "../../configs/Apis";
import { useNavigate } from "react-router-dom";

const WritingEditor = ({ lessonId, lessonContent, lessonType, durationRef }) => {
    const [text, setText] = useState("");
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();

        const userId = localStorage.getItem("userId");
        if (!userId) {
            alert("Bạn chưa đăng nhập.");
            return;
        }

        setLoading(true);

        if (!text.trim()) {
            alert("Vui lòng nhập bài viết trước khi nộp.");
            return;
        }

        // Tạo form-urlencoded payload
        const form = new URLSearchParams();
        form.append("userId", userId);
        form.append("lessonId", lessonId);
        form.append("lessonType", lessonType);
        form.append("prompt", lessonContent);
        form.append("essay", text);

        try {
            const res = await Apis.post(endpoints["writing-evaluate"], form, {
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                }
            });

            const answerId = res.data.answerId;
            navigate(`/writing-result/${answerId}`);
        } catch (err) {
            alert(err?.response?.data?.error || "Đã có lỗi xảy ra.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <Form onSubmit={handleSubmit}>
            <Form.Group className="mb-3">
                <Form.Label>Bài viết</Form.Label>
                <Form.Control
                    as="textarea"
                    rows={10}
                    value={text}
                    onChange={(e) => setText(e.target.value)}
                    placeholder="Viết bài tại đây..."
                    required
                    minWidth={300}
                    maxWidth={800}
                />
            </Form.Group>
            <Button type="submit" variant="success" disabled={loading}>
                {loading ? (
                    <>
                        <span className="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>
                        Đang chấm điểm...
                    </>
                ) : "Nộp bài"}
            </Button>
        </Form>
    );
};

export default WritingEditor;
