import { useEffect, useState } from "react";

const useTextToSpeech = () => {
    const [voices, setVoices] = useState([]);
    const [selectedVoiceIndex, setSelectedVoiceIndex] = useState(0); 

    useEffect(() => {
        const synth = window.speechSynthesis;

        const loadVoices = () => {
            const availableVoices = synth.getVoices();
            setVoices(availableVoices);
        }

        synth.onvoiceschanged = loadVoices;

    }, []);

    const speak = (text, onEnd) => {
        if (!text.trim() || !selectedVoiceIndex) return;

        const utterance = new SpeechSynthesisUtterance(text);
        utterance.voice = voices[selectedVoiceIndex];
        utterance.onend = onEnd;
        utterance.rate = 1; // Tốc độ nói
        utterance.pitch = 1; // Cao độ giọng
        utterance.volume = 1; // Âm lượng
        utterance.lang = voices[selectedVoiceIndex].lang; // Ngôn ngữ
        window.speechSynthesis.speak(utterance);
    };

    return { voices, selectedVoiceIndex, setSelectedVoiceIndex, speak };
};

export default useTextToSpeech;
