import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import Apis, { endpoints } from "../../configs/Apis";

function WritingResult() {
    const { id } = useParams();
    const [result, setResult] = useState(null);
    const [error, setError] = useState("");

    useEffect(() => {
        const loadResult = async () => {
            try {
                const res = await Apis.get(endpoints["writing-result"](id));
                setResult(res.data);
            } catch (err) {
                console.error(err);
                setError("Không thể tải kết quả. Hãy thử lại sau.");
            }
        };

        loadResult();
    }, [id]);

    if (error) return <div className="alert alert-danger mt-3">{error}</div>;
    if (!result) return <div className="mt-3">Đang tải kết quả...</div>;

    return (
        <div className="container mt-4">
            <h2 className="mb-4">Kết quả chấm điểm Writing</h2>

            <h5>Bài viết:</h5>
            <div className="border p-3 mb-3 bg-light" style={{ whiteSpace: "pre-wrap" }}>
                {result.content}
            </div>

            <h5>Phản hồi từ AI:</h5>
            <div className="border p-3 mb-3 bg-light" style={{ whiteSpace: "pre-wrap" }}>
                {result.feedback}
            </div>

            <h5>Điểm số chi tiết:</h5>
            <ul className="list-group">
                <li className="list-group-item">Task Achievement: {result.task_score}</li>
                <li className="list-group-item">Coherence & Cohesion: {result.coherence_score}</li>
                <li className="list-group-item">Lexical Resource: {result.lexical_score}</li>
                <li className="list-group-item">Grammar Range & Accuracy: {result.grammar_score}</li>
                <li className="list-group-item font-weight-bold">Overall Score: {result.overall_score}</li>
            </ul>
        </div>
    );
}

export default WritingResult;