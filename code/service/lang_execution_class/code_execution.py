from abc import ABC, abstractmethod

class CodeExecution(ABC):
    @abstractmethod
    def compile_code(self, source_code: str, unique_id: str) -> bool:
        pass

    @abstractmethod
    def run_code(self, inputs: str) -> tuple[str, str]:
        pass

    @abstractmethod
    def cleanup(self):
        pass
